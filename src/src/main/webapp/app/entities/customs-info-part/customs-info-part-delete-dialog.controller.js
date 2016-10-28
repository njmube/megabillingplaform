(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Customs_info_partDeleteController',Customs_info_partDeleteController);

    Customs_info_partDeleteController.$inject = ['$uibModalInstance', 'entity', 'Customs_info_part'];

    function Customs_info_partDeleteController($uibModalInstance, entity, Customs_info_part) {
        var vm = this;
        vm.customs_info_part = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Customs_info_part.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
