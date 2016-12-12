(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_desc_estateDeleteController',Freecom_desc_estateDeleteController);

    Freecom_desc_estateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_desc_estate'];

    function Freecom_desc_estateDeleteController($uibModalInstance, entity, Freecom_desc_estate) {
        var vm = this;
        vm.freecom_desc_estate = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_desc_estate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
