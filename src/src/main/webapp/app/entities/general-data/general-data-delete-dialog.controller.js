(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('General_dataDeleteController',General_dataDeleteController);

    General_dataDeleteController.$inject = ['$uibModalInstance', 'entity', 'General_data'];

    function General_dataDeleteController($uibModalInstance, entity, General_data) {
        var vm = this;
        vm.general_data = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            General_data.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
