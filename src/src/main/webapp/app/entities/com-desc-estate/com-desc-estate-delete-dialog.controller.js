(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_desc_estateDeleteController',Com_desc_estateDeleteController);

    Com_desc_estateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_desc_estate'];

    function Com_desc_estateDeleteController($uibModalInstance, entity, Com_desc_estate) {
        var vm = this;
        vm.com_desc_estate = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_desc_estate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
