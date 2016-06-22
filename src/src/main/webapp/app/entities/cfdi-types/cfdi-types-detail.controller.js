(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_typesDetailController', Cfdi_typesDetailController);

    Cfdi_typesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Cfdi_types'];

    function Cfdi_typesDetailController($scope, $rootScope, $stateParams, entity, Cfdi_types) {
        var vm = this;

        vm.cfdi_types = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:cfdi_typesUpdate', function(event, result) {
            vm.cfdi_types = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
