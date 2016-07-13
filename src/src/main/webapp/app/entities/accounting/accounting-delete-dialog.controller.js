(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AccountingDeleteController',AccountingDeleteController);

    AccountingDeleteController.$inject = ['$uibModalInstance', 'entity', 'Accounting'];

    function AccountingDeleteController($uibModalInstance, entity, Accounting) {
        var vm = this;
        vm.accounting = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Accounting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
