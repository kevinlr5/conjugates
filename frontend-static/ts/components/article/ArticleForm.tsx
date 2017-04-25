import * as React from 'react';

interface Props {
    handleSubmit: (event: AnalyzeArticleRequest) => void;
}

interface State {
    title: string;
    body: string;
    loading: boolean;
}

export default class ArticleForm extends React.Component<Props, State> {

    constructor(props: Props, context: any) {
        super(props, context);
        this.handleChangeTitle = this.handleChangeTitle.bind(this);
        this.handleChangeBody = this.handleChangeBody.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            title: '',
            body: '',
            loading: false,
        };
    }

    public render() {
        return <div>
                <h4>Enter Article</h4>
                <form className='article' onSubmit={this.handleSubmit}>
                    <p>
                        <label>Title:
                            <input
                                type='text'
                                name='title'
                                value={this.state.title}
                                onChange={this.handleChangeTitle}
                            />
                        </label>
                    </p>
                    <p>
                        <label>Body:
                            <textarea
                                name='body'
                                className='article-body'
                                value={this.state.body}
                                onChange={this.handleChangeBody}
                            />
                        </label>
                    </p>
                    <div>
                        <button type='submit' disabled={!this.isEnabled()}>
                            Submit
                        </button>
                        {this.renderLoading()}
                    </div>
                </form>
            </div>;
    }

    private isEnabled() {
        return this.state.title.length !== 0 && this.state.body.length !== 0;
    }

    private renderLoading() {
        if (this.state.loading) {
            return <span>
                    <i className='fa fa-spinner fa-spin fa-3x fa-fw article-loading-spinner' />
                    <span>Analyzing...</span>
                </span>;
        } else {
            return null;
        }
    }

    private handleChangeTitle(event: React.FormEvent<HTMLInputElement>) {
        this.setState({title: event.currentTarget.value});
    }

    private handleChangeBody(event: React.FormEvent<HTMLTextAreaElement>) {
        this.setState({body: event.currentTarget.value});
    }

    private handleSubmit(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault();
        this.setState({loading: true});
        const request = {
            title: this.state.title,
            body: this.state.body,
        };
        this.props.handleSubmit(request);
    }

}
