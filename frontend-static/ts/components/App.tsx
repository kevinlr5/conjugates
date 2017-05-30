import * as React from 'react';
import {Router, Route, IndexRedirect} from 'react-router';
import {History} from 'history';
import Container from './Container';
import Article from './article/Article';
import About from './About';
import Entity from './Entity';

interface Props {
    history: History;
}

export default class App extends React.Component<Props, {}> {

    public render() {
        return <Router history={this.props.history}>
                <Route path='/' component={Container}>
                    <Route path='about' component={About} />
                    <Route path='article' component={Article} />
                    <Route path='entity/:value' component={Entity} />
                    <IndexRedirect to='article' />
                </Route>
            </Router>;
    }

}
