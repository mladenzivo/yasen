<template>
  <Layout>
    <div v-html="$page.textSubPage.content" />
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
  textSubPage(id: $id) {
    path
  }
}
</page-query>
