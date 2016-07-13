(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Process_typeDeleteController',Process_typeDeleteController);

    Process_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Process_type'];

    function Process_typeDeleteController($uibModalInstance, entity, Process_type) {
        var vm = this;
        vm.process_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Process_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
