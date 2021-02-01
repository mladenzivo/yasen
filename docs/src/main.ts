// This is the main.js file. Import global CSS and scripts here.
// The Client API can be used here. Learn more: gridsome.org/docs/client-api
import { ClientApiConstructor } from './types';

import DefaultLayout from '~/layouts/Default.vue'

const client: ClientApiConstructor = (Vue, { router, head }) => {
  // Set default layout as a global component
  Vue.component('Layout', DefaultLayout)
  head.link?.push({
    rel: 'stylesheet',
    href: '/css/hyde.css'
  })
  head.link?.push({
    rel: 'stylesheet',
    href: '/css/poole.css'
  })
  head.link?.push({
    rel: 'stylesheet',
    href: 'http://fonts.googleapis.com/css?family=PT+Sans:400,400italic,700|Abril+Fatface'
  })
}

export default client
