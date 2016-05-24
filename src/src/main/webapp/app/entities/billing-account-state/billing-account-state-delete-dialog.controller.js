(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Billing_account_stateDeleteController',Billing_account_stateDeleteController);

    Billing_account_stateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Billing_account_state'];

    function Billing_account_stateDeleteController($uibModalInstance, entity, Billing_account_state) {
        var vm = this;
        vm.billing_account_state = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Billing_account_state.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
