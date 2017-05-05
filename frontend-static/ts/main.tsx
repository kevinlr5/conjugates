import * as React from 'react';
import * as ReactDOM from 'react-dom';
import {hashHistory as history} from 'react-router';

import App from './components/App';
import './styles/index.scss';

ReactDOM.render(
    <App history={history} />
, document.getElementById('fixture'));
