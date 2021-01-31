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

<script>
export default {
  name: 'App',

  components: {
    SideBar: () => import('@/components/core/SideBar')
  },

  data: function () {
    return {
    }
  }
}
</script>

<static-query>
query {
  metadata {
    siteName
  }
}
</static-query>
