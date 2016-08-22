(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_specific_descriptionsDeleteController',Freecom_specific_descriptionsDeleteController);

    Freecom_specific_descriptionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_specific_descriptions'];

    function Freecom_specific_descriptionsDeleteController($uibModalInstance, entity, Freecom_specific_descriptions) {
        var vm = this;

        vm.freecom_specific_descriptions = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_specific_descriptions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
