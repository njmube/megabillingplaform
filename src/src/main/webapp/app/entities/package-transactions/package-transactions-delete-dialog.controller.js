(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Package_transactionsDeleteController',Package_transactionsDeleteController);

    Package_transactionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Package_transactions'];

    function Package_transactionsDeleteController($uibModalInstance, entity, Package_transactions) {
        var vm = this;
        vm.package_transactions = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Package_transactions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
