import {get} from './_http';

export default {

    getEntity(value: string) {
        return get(`${__API_URL__}/api/entity/${value}`);
    },

} as EntityApi;
