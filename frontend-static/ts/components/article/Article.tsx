import * as React from 'react';
import ArticleForm from './ArticleForm';
import AnalyzedArticle from './AnalyzedArticle';
import articleApi from '../../api/article';

interface State {
    title: string;
    body: string;
    loading: boolean;
    analyzeArticleResponse: AnalyzeArticleResponse | null;
}

export default class Article extends React.Component<{}, State> {

    constructor(props: {}, context: any) {
        super(props, context);
        this.handleClear = this.handleClear.bind(this);
        this.handleChangeTitle = this.handleChangeTitle.bind(this);
        this.handleChangeBody = this.handleChangeBody.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            title: '',
            body: '',
            loading: false,
            analyzeArticleResponse: null,
        };
    }

    public render() {
        if (this.state.analyzeArticleResponse) {
            return <AnalyzedArticle
                analyzeArticleResponse={this.state.analyzeArticleResponse}
                handleClear={this.handleClear}
            />;
        } else {
            return <ArticleForm
                title={this.state.title}
                body={this.state.body}
                loading={this.state.loading}
                handleChangeTitle={this.handleChangeTitle}
                handleChangeBody={this.handleChangeBody}
                handleSubmit={this.handleSubmit}
            />;
        }
    }

    private handleClear() {
        this.setState({
            title: '',
            body: '',
            loading: false,
            analyzeArticleResponse: null,
        });
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
        this.analyzeArticle(request);
    }

    private analyzeArticle(request: AnalyzeArticleRequest) {
        articleApi.analyzeArticle(request).then(result => {
            this.setState({
                title: '',
                body: '',
                loading: false,
                analyzeArticleResponse: result,
            });
        });
    }

}
