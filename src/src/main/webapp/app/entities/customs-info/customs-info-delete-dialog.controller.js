(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Customs_infoDeleteController',Customs_infoDeleteController);

    Customs_infoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Customs_info'];

    function Customs_infoDeleteController($uibModalInstance, entity, Customs_info) {
        var vm = this;

        vm.customs_info = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Customs_info.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
