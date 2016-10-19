(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taxpayer-request', {
            parent: 'entity',
            url: '/taxpayer-request?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_request.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-request/taxpayer-requests.html',
                    controller: 'Taxpayer_requestController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_request');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taxpayer-request-detail', {
            parent: 'entity',
            url: '/taxpayer-request/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_request.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-request/taxpayer-request-detail.html',
                    controller: 'Taxpayer_requestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_request');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Taxpayer_request', function($stateParams, Taxpayer_request) {
                    return Taxpayer_request.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('taxpayer-request.new', {
            parent: 'taxpayer-request',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-request/taxpayer-request-dialog.html',
                    controller: 'Taxpayer_requestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                account_id: null,
                                ring_pack_id: null,
                                algorithm_signature: null,
                                amount: null,
                                billing_address: null,
                                billing_city: null,
                                billing_country: null,
                                buyer_email: null,
                                buyer_full_name: null,
                                confirmation_url: null,
                                currency: null,
                                description: null,
                                discount: null,
                                extra1: null,
                                extra2: null,
                                extra3: null,
                                lng: null,
                                merchant_id: null,
                                mobile_phone: null,
                                office_telephone: null,
                                payer_document: null,
                                payer_email: null,
                                payer_full_name: null,
                                payer_mobile_phone: null,
                                payer_office_phone: null,
                                payer_phone: null,
                                reference_code: null,
                                response_url: null,
                                shipping_address: null,
                                shipping_city: null,
                                shipping_country: null,
                                signature: null,
                                tax: null,
                                tax_return_base: null,
                                telephone: null,
                                test: null,
                                zipcode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('taxpayer-request', null, { reload: true });
                }, function() {
                    $state.go('taxpayer-request');
                });
            }]
        })
        .state('taxpayer-request.edit', {
            parent: 'taxpayer-request',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-request/taxpayer-request-dialog.html',
                    controller: 'Taxpayer_requestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Taxpayer_request', function(Taxpayer_request) {
                            return Taxpayer_request.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-request', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taxpayer-request.delete', {
            parent: 'taxpayer-request',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-request/taxpayer-request-delete-dialog.html',
                    controller: 'Taxpayer_requestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Taxpayer_request', function(Taxpayer_request) {
                            return Taxpayer_request.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-request', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
