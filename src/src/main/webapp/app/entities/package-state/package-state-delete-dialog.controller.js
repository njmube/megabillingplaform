(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Package_stateDeleteController',Package_stateDeleteController);

    Package_stateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Package_state'];

    function Package_stateDeleteController($uibModalInstance, entity, Package_state) {
        var vm = this;
        vm.package_state = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Package_state.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
