<template>
  <div class="layout">
    <side-bar></side-bar>
    <header class="header">
      <strong>
        <g-link to="/">{{ $static.metadata.siteName }}</g-link>
      </strong>
      <nav class="nav">
        <g-link class="nav__link" to="/">Home</g-link>
        <g-link class="nav__link" to="/about/">About</g-link>
      </nav>
    </header>
    <div class="content container">
      <slot/>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import SideBar from '../components/core/SideBar'

export default Vue.extend({
  name: 'App',

  components: {
    SideBar
  },

  data: function () {
    return {
    }
  }
})
</script>

<static-query>
query {
  metadata {
    siteName
  }
}
</static-query>
