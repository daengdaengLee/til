pub mod components;
mod resources;
mod systems;

use bevy::prelude::*;

use resources::*;
use systems::*;

pub const NUMBER_OF_STARS: usize = 10;
// This is the star sprite size.
pub const STAR_SIZE: f32 = 30.0;

pub struct StarPlugin;

impl Plugin for StarPlugin {
    fn build(&self, app: &mut App) {
        app.init_resource::<StarSpawnTimer>()
            .add_systems(Startup, spawn_stars)
            .add_systems(Update, (tick_star_spawn_timer, spawn_stars_over_time));
    }
}
