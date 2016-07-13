(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_taxregistrationDetailController', Freecom_taxregistrationDetailController);

    Freecom_taxregistrationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_taxregistration', 'Free_cfdi'];

    function Freecom_taxregistrationDetailController($scope, $rootScope, $stateParams, entity, Freecom_taxregistration, Free_cfdi) {
        var vm = this;
        vm.freecom_taxregistration = entity;
        vm.load = function (id) {
            Freecom_taxregistration.get({id: id}, function(result) {
                vm.freecom_taxregistration = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_taxregistrationUpdate', function(event, result) {
            vm.freecom_taxregistration = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
