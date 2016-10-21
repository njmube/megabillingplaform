(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_series_folioDetailController', Taxpayer_series_folioDetailController);

    Taxpayer_series_folioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Taxpayer_series_folio', 'Taxpayer_account'];

    function Taxpayer_series_folioDetailController($scope, $rootScope, $stateParams, entity, Taxpayer_series_folio, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_series_folio = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_series_folioUpdate', function(event, result) {
            vm.taxpayer_series_folio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
