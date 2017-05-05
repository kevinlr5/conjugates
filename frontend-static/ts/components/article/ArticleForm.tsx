import * as React from 'react';

interface Props {
    title: string;
    body: string;
    loading: boolean;
    handleChangeTitle: (event: React.FormEvent<HTMLInputElement>) => void;
    handleChangeBody: (event: React.FormEvent<HTMLTextAreaElement>) => void;
    handleSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
}

export default class ArticleForm extends React.Component<Props, {}> {

    public render() {
        return <div>
                <h4>Enter Article</h4>
                <form className='article' onSubmit={this.props.handleSubmit}>
                    <p>
                        <label>Title:
                            <input
                                type='text'
                                name='title'
                                value={this.props.title}
                                onChange={this.props.handleChangeTitle}
                            />
                        </label>
                    </p>
                    <p>
                        <label>Body:
                            <textarea
                                name='body'
                                className='article-body'
                                value={this.props.body}
                                onChange={this.props.handleChangeBody}
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
        return this.props.title.length !== 0 &&
            this.props.body.length !== 0 &&
            !this.props.loading;
    }

    private renderLoading() {
        if (this.props.loading) {
            return <span>
                    <i className='fa fa-spinner fa-spin fa-3x fa-fw article-loading-spinner' />
                    <span>Analyzing...</span>
                </span>;
        } else {
            return null;
        }
    }

}
