(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_type_docDeleteController',Cfdi_type_docDeleteController);

    Cfdi_type_docDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cfdi_type_doc'];

    function Cfdi_type_docDeleteController($uibModalInstance, entity, Cfdi_type_doc) {
        var vm = this;
        vm.cfdi_type_doc = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Cfdi_type_doc.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
