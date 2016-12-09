(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_desc_stateDeleteController',Com_desc_stateDeleteController);

    Com_desc_stateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_desc_state'];

    function Com_desc_stateDeleteController($uibModalInstance, entity, Com_desc_state) {
        var vm = this;
        vm.com_desc_state = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_desc_state.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
