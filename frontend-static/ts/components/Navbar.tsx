import * as React from 'react';

export default class Navbar extends React.Component<{}, {}> {

    public render() {
        return <div className='navbar-header fixed'>
            <nav className='navbar top-bar' role='navigation'>
                {this.renderTitleArea()}
                {this.renderLinks()}
            </nav>
        </div>;
    }

    private renderTitleArea() {
        return <ul className='title-area'>
            <li className='name'>
                <h1>
                    <a href='#'>
                        <div className='header-logo'>
                            <div className='header-logo-text'>Article Sentiment</div>
                        </div>
                    </a>
                </h1>
            </li>
        </ul>;
    }

    private renderLinks() {
        return <section className='top-bar-section'>
            <ul className='right'>
                <li>
                    <a href='/docs/api.html' target='_blank'>
                        API Docs
                    </a>
                </li>
                <li>
                    <a href='/#/about'>
                        About
                    </a>
                </li>
            </ul>
        </section>;
    }
}
