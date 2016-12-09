(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Type_stateDeleteController',Type_stateDeleteController);

    Type_stateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Type_state'];

    function Type_stateDeleteController($uibModalInstance, entity, Type_state) {
        var vm = this;
        vm.type_state = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Type_state.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
