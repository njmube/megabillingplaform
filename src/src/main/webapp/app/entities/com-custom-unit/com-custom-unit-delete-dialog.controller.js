(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_custom_unitDeleteController',Com_custom_unitDeleteController);

    Com_custom_unitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_custom_unit'];

    function Com_custom_unitDeleteController($uibModalInstance, entity, Com_custom_unit) {
        var vm = this;
        vm.com_custom_unit = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_custom_unit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
