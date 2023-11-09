use bevy::prelude::*;

fn main() {
    App::new()
        .add_plugins(DefaultPlugins)
        .add_plugins(PeoplePlugin)
        .run();
}

pub struct PeoplePlugin;

impl Plugin for PeoplePlugin {
    fn build(&self, app: &mut App) {
        app.add_systems(Startup, setup).add_systems(
            Update,
            (
                print_names,
                people_with_jobs,
                people_ready_for_hire,
                person_does_job,
            ),
        );
    }
}

pub fn setup(mut commands: Commands) {
    commands.spawn((
        Person {
            name: "Alex".to_string(),
        },
        Employed { job: Job::Doctor },
    ));

    // This entity doesn't have the `Employed` Component.
    commands.spawn(Person {
        name: "Bob".to_string(),
    });

    commands.spawn((
        Person {
            name: "Charlie".to_string(),
        },
        Employed {
            job: Job::FireFighter,
        },
    ));

    commands.spawn((
        Person {
            name: "David".to_string(),
        },
        Employed { job: Job::Lawyer },
    ));

    commands.spawn((
        Person {
            name: "Ellen".to_string(),
        },
        Employed {
            job: Job::FireFighter,
        },
    ));
}

pub fn print_names(person_query: Query<&Person>) {
    for person in &person_query {
        println!("Name: {}", person.name);
    }
}

pub fn people_with_jobs(person_query: Query<&Person, With<Employed>>) {
    for person in &person_query {
        println!("{} has a job.", person.name);
    }
}

pub fn people_ready_for_hire(person_query: Query<&Person, Without<Employed>>) {
    for person in &person_query {
        println!("{} is ready for hire.", person.name);
    }
}

pub fn person_does_job(person_query: Query<(&Person, &Employed)>) {
    for (person, employed) in &person_query {
        let job_name = match employed.job {
            Job::Doctor => "Doctor",
            Job::FireFighter => "FireFighter",
            Job::Lawyer => "Lawyer",
        };
        println!("{} is a {}.", person.name, job_name);
    }
}

#[derive(Component)]
pub struct Person {
    pub name: String,
}

#[derive(Component)]
pub struct Employed {
    pub job: Job,
}

#[derive(Debug)]
pub enum Job {
    Doctor,
    FireFighter,
    Lawyer,
}
