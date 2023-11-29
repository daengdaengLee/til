use bevy::prelude::*;

use crate::game::SimulationState;

pub fn pause_simulation(mut next_simulation_state: ResMut<NextState<SimulationState>>) {
    next_simulation_state.set(SimulationState::Paused);
}

pub fn resume_simulation(mut next_simulation_state: ResMut<NextState<SimulationState>>) {
    next_simulation_state.set(SimulationState::Running);
}

pub fn toggle_simulation(
    keyboard_input: Res<Input<KeyCode>>,
    simulation_state: Res<State<SimulationState>>,
    mut next_simulation_state: ResMut<NextState<SimulationState>>,
) {
    if keyboard_input.just_pressed(KeyCode::Space) {
        if simulation_state.get() == &SimulationState::Running {
            next_simulation_state.set(SimulationState::Paused);
            println!("Simulation Paused.");
        }
        if simulation_state.get() == &SimulationState::Paused {
            next_simulation_state.set(SimulationState::Running);
            println!("Simulation Running.");
        }
    }
}
