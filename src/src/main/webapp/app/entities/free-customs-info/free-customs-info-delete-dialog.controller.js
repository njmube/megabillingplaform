(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_customs_infoDeleteController',Free_customs_infoDeleteController);

    Free_customs_infoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_customs_info'];

    function Free_customs_infoDeleteController($uibModalInstance, entity, Free_customs_info) {
        var vm = this;
        vm.free_customs_info = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Free_customs_info.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
