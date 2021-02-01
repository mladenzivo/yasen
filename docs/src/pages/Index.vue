<template>
  <Layout>
    <h1>Yet Another Selenium Enhancer</h1>
    <p>Yasen is a <a href="https://selenium.dev/">Selenium</a> wrapper designed with the following ideas in mind:</p>
    <ul>
      <li>object-oriented approach to code,</li>
      <li>laziness of element finding commands,</li>
      <li>repeating commands until success (or timeout).</li>
    </ul>
    <pre class="language-java"><code class="language-java">Browser browser = new Browser(new FirefoxDriver());</code></pre>
  </Layout>
</template>

<script lang="ts">
import Vue from 'vue'
import { highlightAll } from 'prismjs'
import 'prismjs/themes/prism.css'
import 'prismjs/components/prism-java'

export default Vue.extend({
  metaInfo: {
    title: 'Yet Another Selenium Enhancer'
  },
  mounted () {
    highlightAll()
  }
})
</script>
