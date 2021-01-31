<template>
  <Layout>
    <div v-html="$page.textPage.content" />
  </Layout>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  data: function () {
    return {}
  }
})
</script>

<page-query>
query($id: ID!) {
  textPage(id: $id) {
    content
  }
}
</page-query>
