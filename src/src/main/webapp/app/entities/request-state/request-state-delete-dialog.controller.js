(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Request_stateDeleteController',Request_stateDeleteController);

    Request_stateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Request_state'];

    function Request_stateDeleteController($uibModalInstance, entity, Request_state) {
        var vm = this;
        vm.request_state = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Request_state.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
