pub mod components;
mod systems;

use bevy::prelude::*;

use crate::{game::SimulationState, AppState};

use systems::*;

// #[derive(SystemSet, Debug, Hash, PartialEq, Eq, Clone)]
// pub struct MovementSystemSet;

// #[derive(SystemSet, Debug, Hash, PartialEq, Eq, Clone)]
// pub struct ConfinementSystemSet;

#[derive(SystemSet, Debug, Hash, PartialEq, Eq, Clone)]
pub enum PlayerSystemSet {
    Movement,
    Confinement,
}

pub struct PlayerPlugin;

impl Plugin for PlayerPlugin {
    fn build(&self, app: &mut App) {
        app.configure_sets(
            Update,
            PlayerSystemSet::Movement.before(PlayerSystemSet::Confinement),
        )
        .add_systems(OnEnter(AppState::Game), spawn_player)
        .add_systems(
            Update,
            (
                player_movement.in_set(PlayerSystemSet::Movement),
                confine_player_movement.in_set(PlayerSystemSet::Confinement),
                enemy_hit_player,
                player_hit_star,
            )
                .run_if(in_state(AppState::Game))
                .run_if(in_state(SimulationState::Running)),
        )
        .add_systems(OnExit(AppState::Game), despawn_player);
    }
}
