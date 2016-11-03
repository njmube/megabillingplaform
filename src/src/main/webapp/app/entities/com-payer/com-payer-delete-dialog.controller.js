(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_payerDeleteController',Com_payerDeleteController);

    Com_payerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_payer'];

    function Com_payerDeleteController($uibModalInstance, entity, Com_payer) {
        var vm = this;
        vm.com_payer = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_payer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
