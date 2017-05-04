import * as Redux from 'redux';

function applyHandler<S, A extends Action>(state: S, action: A, handler?: ActionHandler<S, A>): S {
    return handler ? Object.assign({}, state, handler(state, action)) : state;
}

export function createReducer<S>(initialState: S, handlers: ActionHandlers<S>): Redux.Reducer<S> {
    return (state = initialState, action: Action) => {
        return applyHandler(state, action, handlers[action.type]);
    };
}
