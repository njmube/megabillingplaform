(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_state_eventDeleteController',C_state_eventDeleteController);

    C_state_eventDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_state_event'];

    function C_state_eventDeleteController($uibModalInstance, entity, C_state_event) {
        var vm = this;
        vm.c_state_event = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_state_event.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
