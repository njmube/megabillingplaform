(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_speiDeleteController',Freecom_speiDeleteController);

    Freecom_speiDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_spei'];

    function Freecom_speiDeleteController($uibModalInstance, entity, Freecom_spei) {
        var vm = this;

        vm.freecom_spei = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_spei.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
