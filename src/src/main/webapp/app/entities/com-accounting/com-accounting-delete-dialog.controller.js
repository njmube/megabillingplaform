(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_accountingDeleteController',Com_accountingDeleteController);

    Com_accountingDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_accounting'];

    function Com_accountingDeleteController($uibModalInstance, entity, Com_accounting) {
        var vm = this;
        vm.com_accounting = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_accounting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
