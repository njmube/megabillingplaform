(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_stateDeleteController',C_stateDeleteController);

    C_stateDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_state'];

    function C_stateDeleteController($uibModalInstance, entity, C_state) {
        var vm = this;
        vm.c_state = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_state.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
