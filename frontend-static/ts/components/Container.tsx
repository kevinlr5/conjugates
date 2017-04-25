import * as React from 'react';
import Navbar from './Navbar';

export default class Container extends React.Component<{}, {}> {

    public render() {
        return <div>
                <Navbar />
                <div className='main-container'>
                    {this.props.children}
                </div>
            </div>;
    }

}
