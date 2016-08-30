(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Request_taxpayer_accountDeleteController',Request_taxpayer_accountDeleteController);

    Request_taxpayer_accountDeleteController.$inject = ['$uibModalInstance', 'entity', 'Request_taxpayer_account'];

    function Request_taxpayer_accountDeleteController($uibModalInstance, entity, Request_taxpayer_account) {
        var vm = this;

        vm.request_taxpayer_account = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Request_taxpayer_account.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
