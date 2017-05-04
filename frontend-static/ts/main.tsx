import * as React from 'react';
import * as ReactDOM from 'react-dom';
import * as Redux from 'redux';
import thunk from 'redux-thunk';
import {hashHistory as history} from 'react-router';
import { Provider } from 'react-redux';
import {routerMiddleware, syncHistoryWithStore} from 'react-router-redux';

import reducer from './reducers';
import App from './components/App';
import './styles/index.scss';

const middleware: Redux.Middleware[] = [
    thunk,
    routerMiddleware(history),
];

const store = Redux.createStore(reducer,  Redux.applyMiddleware(...middleware));
const syncedHistory = syncHistoryWithStore(history, store);

ReactDOM.render(
    <Provider store={store}>
        <App history={syncedHistory} />
    </Provider>
, document.getElementById('fixture'));
