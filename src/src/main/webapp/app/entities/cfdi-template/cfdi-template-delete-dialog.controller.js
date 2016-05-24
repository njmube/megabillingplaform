(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_templateDeleteController',Cfdi_templateDeleteController);

    Cfdi_templateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cfdi_template'];

    function Cfdi_templateDeleteController($uibModalInstance, entity, Cfdi_template) {
        var vm = this;
        vm.cfdi_template = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Cfdi_template.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
