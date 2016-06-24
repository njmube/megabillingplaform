(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_type_docDetailController', Cfdi_type_docDetailController);

    Cfdi_type_docDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Cfdi_type_doc'];

    function Cfdi_type_docDetailController($scope, $rootScope, $stateParams, entity, Cfdi_type_doc) {
        var vm = this;
        vm.cfdi_type_doc = entity;
        vm.load = function (id) {
            Cfdi_type_doc.get({id: id}, function(result) {
                vm.cfdi_type_doc = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:cfdi_type_docUpdate', function(event, result) {
            vm.cfdi_type_doc = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
