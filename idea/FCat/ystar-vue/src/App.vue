<template>
  <v-app>
    <core-filter />
    <core-toolbar />
    <core-drawer />
    <core-view />
  </v-app>
</template>

<style lang="scss">
  @import '@/styles/index.scss';
  /* Remove in 1.2 */
  .v-datatable thead th.column.sortable i {
    vertical-align: unset;
  }
   #nprogress {
      position: fixed;
      left: 0;
      top: 0;
      width: 100%;
      z-index: 100;
  }
  #nprogress .bar {
    background: #00aec5;
    height: 3px;
  }
  #nprogress .spinner-icon {
    border-top-color: #00aec5;
    border-left-color: #00aec5;
  }
</style>
