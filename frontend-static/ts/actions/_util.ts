function asyncRequest(requestId: number): AsyncRequestAction {
    return {type: 'ASYNC_REQUEST', requestId};
}

function asyncResponse(requestId: number, error: Error | null): AsyncResponseAction {
    return {type: 'ASYNC_RESPONSE', requestId, error};
}

let requestIdCounter = 0;

export function getAsyncActionCreator<P, R>(
        fn: (params: P) => Promise<R>,
        handler: (result: R) => Action): AsyncActionCreator<P, R> {
    return params => dispatch => {
        const requestId = ++requestIdCounter;
        dispatch(asyncRequest(requestId));
        return Promise.resolve()
            .then(() => fn(params))
            .then(result => {
                dispatch(asyncResponse(requestId, null));
                dispatch(handler(result));
                return result;
            })
            .catch(error => {
                dispatch(asyncResponse(requestId, error));
                throw error;
            });
    };
}
