import * as React from 'react';

interface Props {
    message: string;
}

export default class App extends React.Component<Props, {}> {

    public render() {
        return <div className='app'>
            <h1>{this.props.message}</h1>
        </div>;
    }

}
