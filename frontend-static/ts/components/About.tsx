import * as React from 'react';
import infoApi from '../api/info';

interface State {
    info: InfoResponse | null;
}

export default class About extends React.Component<{}, State> {

    constructor(props: {}, context: any) {
        super(props, context);
        this.state = {
            info: null,
        };
    }

    public componentDidMount() {
        this.loadInfo();
    }

    public render() {
        return <div>
                <h4>About</h4>
                <h5 className='subheader'>How it works</h5>
                {this.renderHow()}
                <h5 className='subheader'>Server Info</h5>
                {this.renderInfo()}
                <h5 className='subheader'>Author</h5>
                {this.renderAuthor()}
            </div>;
    }

    private renderHow() {
        return <p>
                Articles are analyzed using the Stanford CoreNLP library. Documents go through
                entity extraction and sentence by sentence parsing. Basic sentiment analysis
                is performed on each reference to an entity. Statistics from these tasks are aggregated and
                returned from the server.
            </p>;
    }

    private renderInfo() {
        if (this.state.info) {
            return <ul className='square indented-section'>
                    <li>Github: <a target='_blank' href='https://github.com/kevinlr5/sentiment-analyzer'>
                            https://github.com/kevinlr5/sentiment-analyzer
                        </a>
                    </li>
                    <li>Name: {this.state.info.name}</li>
                    <li>Version: {this.state.info.version}</li>
                    <li>Commit Hash: {this.state.info.commitHash}</li>
                </ul>;
        } else {
            return <span className='indented-section'>
                    <i className='fa fa-spinner fa-spin fa-3x fa-fw article-loading-spinner' />
                    <span>Loading server info...</span>
                </span>;
        }
    }

    private renderAuthor() {
        return <p>
                My name is Kevin Richards. I'm a software engineer living in the Salt Lake Valley.
            </p>;
    }

    private loadInfo() {
        infoApi.getInfo().then(result => {
            this.setState({
                info: result,
            });
        });
    }

}
