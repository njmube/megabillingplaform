(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountTaxerHomeController', Taxpayer_accountTaxerHomeController);

    Taxpayer_accountTaxerHomeController.$inject = ['entity', 'Taxpayer_account'];

    function Taxpayer_accountTaxerHomeController(entity, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query({page: 0, size: 10});
    }
})();
