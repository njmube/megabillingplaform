(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Type_taxpayerDeleteController',Type_taxpayerDeleteController);

    Type_taxpayerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Type_taxpayer'];

    function Type_taxpayerDeleteController($uibModalInstance, entity, Type_taxpayer) {
        var vm = this;
        vm.type_taxpayer = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Type_taxpayer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
