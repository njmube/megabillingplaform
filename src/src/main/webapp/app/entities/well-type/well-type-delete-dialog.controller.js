(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Well_typeDeleteController',Well_typeDeleteController);

    Well_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Well_type'];

    function Well_typeDeleteController($uibModalInstance, entity, Well_type) {
        var vm = this;
        vm.well_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Well_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
