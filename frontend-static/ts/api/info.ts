import {get} from './_http';

export default {

    getInfo() {
        return get(`${__API_URL__}/api/info`);
    },

} as InfoApi;
